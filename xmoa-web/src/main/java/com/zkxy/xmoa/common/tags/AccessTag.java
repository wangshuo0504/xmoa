package com.zkxy.xmoa.common.tags;


import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company:源本信息科技有限公司 Co., Ltd.</p>
 * @author Caiming
 * @version 1.0
 * 修改记录：
 * 修改序号，修改日期，修改人，修改内容
 */
public class AccessTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9135494673405050704L;

	/*@Autowired
	private RoleService roleService;

	@Autowired
	private SystemFunctionService systemFunctionService;*/
	
	private String validateUrl;

	@Override
	public int doStartTag() throws JspException {
		
		HttpSession session = super.pageContext.getSession();
		/*systemFunctionService = (SystemFunctionService) SpringWiredBean.getInstance().getBeanById("systemFunctionService");
		roleService = (RoleService) SpringWiredBean.getInstance().getBeanById("roleService");
		List<SystemFunction> list = systemFunctionService.queryFunctionByUrl(validateUrl);
        if(list==null||list.isEmpty()){
        	return TagSupport.EVAL_BODY_INCLUDE;
        }
        List<Roles> rolesList = (List<Roles>)session.getAttribute("currer_roles");
        for(Roles role:rolesList){
        	List<String> urlList = new ArrayList<String>();
			try {
				urlList = roleService.selectFunUrlByRoleId(role.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
    		for(String url : urlList){
    			if(validateUrl.indexOf(url)!=-1){
    				return TagSupport.EVAL_BODY_INCLUDE;
    			}
    		}
        }*/
		
        return TagSupport.SKIP_BODY;
		
		
		
		
	}

	public String getValidateUrl() {
		return validateUrl;
	}

	public void setValidateUrl(String validateUrl) {
		this.validateUrl = validateUrl;
	}

}
