package com.project.statLogger;


import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidDataSourceStatLoggerAdapter;
import com.alibaba.druid.pool.DruidDataSourceStatValue;

public class MyStatLogger extends DruidDataSourceStatLoggerAdapter implements DruidDataSourceStatLogger
{
	/**
	 * Logger for this class
	 */
//	private static final Logger logger = Logger.getLogger(MyStatLogger.class);

	@Override
	public void log(DruidDataSourceStatValue statValue)
	{
//		logger.info(statValue.toString());
	}
	
}
