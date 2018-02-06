package com.grgbanking.framework.manager.mapper;

import com.grgbanking.framework.domains.manager.authority.param.AuthorityListParam;
import com.grgbanking.framework.domains.manager.authority.pojo.AuthorityPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("AuthorityMapper")
public interface AuthorityMapper extends BaseMapper {

	List<AuthorityPojo> getAuthorityList(@Param("authorityListParam") AuthorityListParam authorityListParam, @Param("list") List<AuthorityPojo> pauthorityList);

	void addAuthority(AuthorityPojo authorityPojo);

	Long selectMaxSortId(@Param("pid") Long pid);

	void deleteAuthority(AuthorityPojo authorityPojo);

	void editAuthority(AuthorityPojo authorityPojo);

	Long getAuthorityCount(@Param("authorityListParam") AuthorityListParam authorityListParam);

	Integer checkIsExistAuthority(AuthorityPojo authorityPojo);

	List<AuthorityPojo>  getAllAuthority();

	List<AuthorityPojo>  getAllAuthorityByAdmin();

	List<AuthorityPojo>  getAllAuthorityByPid();

	AuthorityPojo  getAuthorityInfo(AuthorityPojo authorityPojo);

	Integer checkIsRoleRelate(AuthorityPojo authorityPojo);

}
