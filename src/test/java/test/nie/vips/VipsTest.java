package test.nie.vips;

import org.junit.Test;

import com.nie.vips.Vips;

public class VipsTest {

	@Test
	public void extrateBolck() {
		Vips vips=new Vips("http://www.cankaoxiaoxi.com/roll10/bd/20161227/1556309.shtml");
		vips.setRound(3);
		vips.service();
	}
	
}
