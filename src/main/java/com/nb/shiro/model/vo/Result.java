package com.nb.shiro.model.vo;

import com.nb.shiro.enums.ErrorEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 接口返回数据格式
 */
@Data
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 返回处理消息
	 */
	private String message ;

	/**
	 * 返回代码
	 */

	private Integer code;

	/**
	 * 返回数据对象 data
	 */
	private T data;
	


	public Result() {
		
	}
	




	
	public static<T> Result<T> OK() {
		Result<T> r = new Result<T>();
		r.setCode(ErrorEnum.SUCCESS.getCode());
		r.setMessage(ErrorEnum.SUCCESS.getMessage());
		return r;
	}
	
	public static<T> Result<T> OK(T data) {
		Result<T> r = new Result<T>();
		r.setCode(ErrorEnum.SUCCESS.getCode());
		r.setMessage(ErrorEnum.SUCCESS.getMessage());
		r.setData(data);
		return r;
	}
	

	

	
	public static Result<Object> error(int code, String msg) {
		Result<Object> r = new Result<Object>();
		r.setCode(code);
		r.setMessage(msg);
		return r;
	}

	public static Result<Object> error(ErrorEnum errorEnum) {
		Result<Object> r = new Result<Object>();
		r.setCode(errorEnum.getCode());
		r.setMessage(errorEnum.getMessage());
		return r;
	}

	public static Result<Object> error500(String msg) {
		Result<Object> r = new Result<Object>();
		r.setMessage(msg);
		r.setCode(ErrorEnum.ERROR.getCode());
		return r;
	}


	/**
	 * 无权限访问返回结果
	 */
	public static Result<Object> noauth() {
		return error(ErrorEnum.SORRY403.getCode(), ErrorEnum.SORRY403.getMessage());
	}
}