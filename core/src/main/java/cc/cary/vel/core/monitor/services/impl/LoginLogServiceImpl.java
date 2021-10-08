package cc.cary.vel.core.monitor.services.impl;

import cc.cary.vel.core.common.libs.QueryRequest;
import cc.cary.vel.core.common.utils.QueryUtils;
import cc.cary.vel.core.monitor.entities.LoginLog;
import cc.cary.vel.core.monitor.mapper.LoginLogMapper;
import cc.cary.vel.core.monitor.services.ILoginLogService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * LoginLogServiceImpl
 *
 * @author Cary
 * @date 2021/05/22
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {
  @Override
  public IPage<LoginLog> findForPage(QueryRequest request, Map<String, Object> params) {
    QueryUtils.parseParamsDate(params, "loginTimeStart", "loginTimeEnd");
    return this.baseMapper.findForPage(QueryUtils.buildPage(request, true), params);
  }

  @Override
  public int batchCount(Map<String, Object> params) {
    return this.baseMapper.batchCount(params);
  }

  @Override
  public List<LoginLog> batchData(int offset, int limit, Map<String, Object> params) {
    return this.baseMapper.batchData(offset, limit, params);
  }
}
