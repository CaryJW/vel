package cc.cary.vel.core.monitor.services.impl;

import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.common.utils.AddressUtils;
import cc.cary.vel.core.common.utils.VelFileUtils;
import cc.cary.vel.core.common.utils.QueryUtils;
import cc.cary.vel.core.monitor.entities.Log;
import cc.cary.vel.core.monitor.mapper.LogMapper;
import cc.cary.vel.core.monitor.services.ILogService;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * LogServiceImpl
 *
 * @author Cary
 * @date 2021/05/22
 */
@Service
@Slf4j
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {
  @Override
  public IPage<Log> findForPage(QueryRequest request, Map<String, Object> params) {
    QueryUtils.parseParamsDate(params, "createTimeStart", "createTimeEnd");
    return this.baseMapper.findForPage(QueryUtils.buildPage(request, true), params);
  }

  @Override
  public void saveLog(ProceedingJoinPoint point, Log log) {
    MethodSignature signature = (MethodSignature) point.getSignature();
    Method method = signature.getMethod();
    cc.cary.vel.core.common.annotation.Log logAnnotation = method.getAnnotation(cc.cary.vel.core.common.annotation.Log.class);
    if (logAnnotation != null) {
      // 注解上的描述
      log.setOperation(logAnnotation.value());
      // 日志类型
      log.setType(logAnnotation.type());
    }
    // 请求的类名
    String className = point.getTarget().getClass().getName();
    // 请求的方法名
    String methodName = signature.getName();
    log.setMethod(className + "." + methodName + "()");
    // 请求的方法参数值
    Object[] args = point.getArgs();
    // 请求的方法参数名称
    LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
    String[] paramNames = u.getParameterNames(method);
    if (args != null && paramNames != null) {
      StringBuilder params = new StringBuilder();
      params = handleParams(params, args, Arrays.asList(paramNames));
      log.setParams(params.toString());
    }
    log.setLocation(AddressUtils.getCityInfo(log.getIp()));
    // 保存系统日志
    this.save(log);
  }

  @Override
  public void export(Map<String, Object> params, HttpServletResponse response) {
    try {
      File file = File.createTempFile("temp_", ".xlsx");
      List<Log> data = this.baseMapper.getList(params);
      EasyExcel.write(file, Log.class).sheet("模板").doWrite(data);
      VelFileUtils.download(file, "系统日志.xlsx", true, response);
    } catch (Exception e) {
      log.error("系统日志导出失败：", e);
    }
  }

  @Override
  public int batchCount(Map<String, Object> params) {
    return this.baseMapper.batchCount(params);
  }

  @Override
  public List<Log> batchData(int offset, int limit, Map<String, Object> params) {
    return this.baseMapper.batchData(offset, limit, params);
  }

  private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) {
    for (int i = 0; i < args.length; i++) {
      if (args[i] instanceof Map) {
        Set set = ((Map) args[i]).keySet();
        List<Object> list = new ArrayList<>();
        List<Object> paramList = new ArrayList<>();
        for (Object key : set) {
          list.add(((Map) args[i]).get(key));
          paramList.add(key);
        }
        return handleParams(params, list.toArray(), paramList);
      } else {
        if (args[i] instanceof Serializable) {
          Class<?> aClass = args[i].getClass();
          try {
            aClass.getDeclaredMethod("toString", new Class[]{null});
            // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
            params.append(" ").append(paramNames.get(i)).append(": ").append(JSON.toJSON(args[i]));
          } catch (NoSuchMethodException e) {
            params.append(" ").append(paramNames.get(i)).append(": ").append(JSON.toJSON(args[i].toString()));
          }
        } else if (args[i] instanceof MultipartFile) {
          MultipartFile file = (MultipartFile) args[i];
          params.append(" ").append(paramNames.get(i)).append(": ").append(file.getName());
        } else {
          params.append(" ").append(paramNames.get(i)).append(": ").append(args[i]);
        }
      }
    }
    return params;
  }
}
