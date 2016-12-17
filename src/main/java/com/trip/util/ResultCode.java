package com.trip.util;


/**
 * <p>定义 service 层的返回值</p>
 * @author wangxichun
 * 
 */
public class ResultCode {

	/** 成功 */
	public static final int SUCCESS = 0;

	/** 失败 */
	public static final int FAILURE = -1;

	/** 参数错误 */
	public static final int PARAMETER_ERROR = 1;
	
	/** 数据库错误 */
	public static final int DB_ERROR = 2;
	
	/** 未登录 */
	public static final int NOT_LOGIN = 3;
	
	/** 用户不存在 **/
	public static final int USER_NOT_EXIST = 4;
	
	/** 海度动态口令验证失败**/
	public static final int HIIDO_VALIDATE_FAILURE = 5;
	
	/** 其他错误 */
	public static final int OTHER_ERROR = -999999;

}
