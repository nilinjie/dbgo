package cn.com.dbgo.core.mapper;

import cn.com.dbgo.core.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户mapper层
 *
 * @author lingee
 * @date 2022/8/18
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
