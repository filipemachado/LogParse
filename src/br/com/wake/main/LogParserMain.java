package br.com.wake.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Classe utilizada para direcionar a execução de acordo com os parametros.
 */

public class LogParserMain {
	
	static int PARAM_LENGTH = 2;
	static String REPORT_PARSE_LOG = "report_parse_log";
	static String REPORT_MEANS_DEATH = "report_means_death";
    /**
     * Método inicial que recebe os parametros, e direciona para outros métodos de acordo com o segundo paramentro 
     * classe correta e age de uma forma especifica.
     * 
     * @param args[0] File inputLogFile - caminho onde estará o arquivo de log. 
     * @param args[1] File inputReportAction - ação a ser tomada [report_parse_log ou report_means_death]  
     * 
     * @throws Exception 
     * 
     * @version 1.0
     * @author filipemachado    
 	 * @since 17/09/2015
     */	

	public static void main(String[] args) throws Exception {   
		try {
			if(args.length == PARAM_LENGTH){
				LogParserBusiness logParserBusiness = new LogParserBusiness(); 
				if(args[1].toString().equals(REPORT_PARSE_LOG))
					logParserBusiness.reportLogParse(new File(args[0].toString()));
				else if(args[1].toString().equals(REPORT_MEANS_DEATH))
					logParserBusiness.reportMeansDeath(new File(args[0].toString()));
				else
					throw new Exception("Invalid Param 'inputReportAction'");
			}else{
				throw new Exception("Invalid number of parameters");
			}
		} catch (Exception ex) {
			System.out.println("There was a problem: [" + ex.getMessage() + "]");
			
		}	
	}

}
