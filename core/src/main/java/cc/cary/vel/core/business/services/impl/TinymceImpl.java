package cc.cary.vel.core.business.services.impl;

import cc.cary.vel.core.business.entities.Tinymce;
import cc.cary.vel.core.business.mapper.TinymceMapper;
import cc.cary.vel.core.business.services.ITinymceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * TinymceImpl
 *
 * @author Cary
 * @date 2021/05/22
 */
@Service
public class TinymceImpl extends ServiceImpl<TinymceMapper, Tinymce> implements ITinymceService {
}
