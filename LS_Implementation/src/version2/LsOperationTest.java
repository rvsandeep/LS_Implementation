package version2;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.apache.commons.io.*;

public class LsOperationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1Main() throws IOException {
		String[] args = {"ls"};
		LsOperation.main(args);
		boolean state = FileUtils.contentEquals(new File("out.txt"),new File("expectedouputs/contents.txt"));
		assertTrue(state);		

	}
	
	@Test
	public void test2Main() throws IOException {
		String[] args = {"ls","-a"};
		LsOperation.main(args);
		boolean state = FileUtils.contentEquals(new File("out.txt"),new File("expectedouputs/smalla.txt"));
		assertTrue(state);		

	}
	
	@Test
	public void test3Main() throws IOException {
		String[] args = {"ls","-U","testsFolder/CreationTime"};
		LsOperation.main(args);
		boolean state = FileUtils.contentEquals(new File("out.txt"),new File("expectedouputs/Creation.txt"));
		assertTrue(state);		
	}
	
	
	@Test
	public void test4Main() throws IOException {
		String[] args = {"ls","-u","testsFolder/lastAccessed"};
		LsOperation.main(args);
		boolean state = FileUtils.contentEquals(new File("out.txt"),new File("expectedouputs/accessed.txt"));
		assertTrue(state);		
	}
	
	@Test
	public void test5Main() throws IOException {
		String[] args = {"ls","-t","testsFolder/Modified"};
		LsOperation.main(args);
		boolean state = FileUtils.contentEquals(new File("out.txt"),new File("expectedouputs/modify.txt"));
		assertTrue(state);		
	}
	
}
