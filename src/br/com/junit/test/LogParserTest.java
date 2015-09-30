package br.com.junit.test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.wake.main.LogParserBusiness;

public class LogParserTest extends LogParserBusiness {

	
 	
	@Test
	public void testPlayer() {
		String line = "20:34 ClientUserinfoChanged: 2 n\\Isgalamido\\t\0\\model\\xian/default\\hmodel\\xian/default\\g_redteam\\g_blueteam\\c1\\4\\c2\\5\\hc\\100\\w\\0\\l\\0\tt\\0\\tl\\0";
		String expected = "Isgalamido";
		String result = getPlayer(line);
		assertEquals(expected, result, 0);
	}
	
	@Test
	public void testKiled() {
		String line = "14:02 Kill: 1022 5 22: <world> killed Assasinu Credi by MOD_TRIGGER_HURT";
		String expected = "Assasinu Credi";
		String result = getKilled(line);
		assertEquals(expected, result, 0);
	}
	
	@Test
	public void testMeansDeath() {
		String line = "14:02 Kill: 1022 5 22: <world> killed Assasinu Credi by MOD_TRIGGER_HURT";
		String expected = "MOD_TRIGGER_HURT";
		String result = getMeansDeath(line);
		assertEquals(expected, result, 0);
	}
	
	@Test
	public void testIsTag() {
		String line = "14:02 Kill: 1022 5 22: <world> killed Assasinu Credi by MOD_TRIGGER_HURT";
		String tag = "Kill";
		boolean result = isTag(line, tag);
		assertTrue(result);
	}
	
	

}
