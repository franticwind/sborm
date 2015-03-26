package com.sborm.core.grammar;

/**
 * 语法组装异常类
 * @author fengli
 * @date 2014-7-8 下午3:11:03
 *
 */
public class GrammarException extends RuntimeException {

	private static final long serialVersionUID = -6861864807376509831L;

	public GrammarException(String msg) {
		super(msg);
	}
}
