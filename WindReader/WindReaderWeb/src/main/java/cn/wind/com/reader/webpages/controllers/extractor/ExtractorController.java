package cn.wind.com.reader.webpages.controllers.extractor;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.SAXException;

import cn.wind.com.page.mine.PageMine;
import cn.wind.com.page.mine.model.PlainContent;
import cn.wind.com.page.mine.utils.HttpUtils;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("")
public class ExtractorController {
	
	@Get("")
	public String get(@Param("link") String link,Invocation inv) throws ParserConfigurationException, BoilerpipeProcessingException, IOException, SAXException{
		if(StringUtils.isNotEmpty(link)){
			PageMine pm = new PageMine();
			PlainContent pc = HttpUtils.getHtmlContent(link, 5000);
			PlainContent result = pm.parseHtmlByCx(pc);
			//PlainContent pc = pm.parseHtmlByBipe(link);
			inv.addModel("plainContent", result);
		}
		return "extract";
	}
}
