package com.osrc.stats.service;

import com.osrc.stats.pojo.UserStatsContent;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.util.XMLResourceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;
import org.w3c.dom.svg.SVGImageElement;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * svg渲染
 * @author tom
 * @date 2022/5/16 16:26
 */
@Service
public class SvgService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SvgService.class);

	private static final String SVG_CONTENT_REPLACE_KEY = "svgContent";

	private static Map<String, Document> cacheDocMap = new ConcurrentHashMap<>(8);

	public Document renderUserStatsSvg(UserStatsContent statsContent) {
		Document doc = getDocument("templates/stats-black.ftl");
		HashMap<String, String> paramMap = new HashMap<>(16);

		fillById(doc, paramMap);
		return doc;
	}

	private Document getDocument(String fileName) {
		Document document = cacheDocMap.get(fileName);
		if (document == null) {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
			try {
				document = factory.createSVGDocument(null, inputStream);
				cacheDocMap.put(fileName, document);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return (Document) document.cloneNode(true);
	}

	private void fillById(Document doc, Map<String, String> paramMap) {
		// 遍历参数，填充数据
		Set<String> keySet = paramMap.keySet();
		Object conf;
		Element temp;
		for (String key : keySet) {
			temp = doc.getElementById(key);
			if (temp == null) {
				continue;
			}

			conf = paramMap.get(key);
			if (conf instanceof String) {
				fillTagValue(temp, (String) conf);
			} else if (conf instanceof Map) {
				// 表示需要修改标签的内容， 修改标签的属性， 这个时候就需要遍历替换
				// 约定 key 为 {@link SVG_CONTENT_REPLACE_KEY} 的表示需要替换内容
				// 其他的表示根据传入的kv替换属性
				for (Object entry : ((Map) conf).entrySet()) {
					if (SVG_CONTENT_REPLACE_KEY.equals(((Map.Entry) entry).getKey() + "")) {
						fillTagValue(temp, ((Map.Entry) entry).getValue() + "");
					} else {
						temp.setAttribute(((Map.Entry) entry).getKey() + "", ((Map.Entry) entry).getValue() + "");
					}
				}
			}
		}
	}

	private void fillTagValue(Element e, String val) {
		if (e instanceof SVGImageElement) {
			((SVGImageElement) e).getHref().setBaseVal(val);
		} else {
			e.setTextContent(val);
		}
	}

	public static void main(String[] args) {
		// Create an SVG document.
		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		SVGDocument doc = (SVGDocument) impl.createDocument(svgNS, "svg", null);

		// Create a converter for this document.
		SVGGraphics2D graphics2D = new SVGGraphics2D(doc);

		Font font = new Font(Font.SERIF, Font.PLAIN, 18);
		graphics2D.setFont(font);
		graphics2D.setColor(Color.BLACK);
		graphics2D.drawString("User Stats", 10,10);
		font = new Font(Font.SERIF, Font.PLAIN, 10);
		graphics2D.setFont(font);
		graphics2D.drawString("Total Star Earned", 60, 80);
		graphics2D.drawString("Total Watched", 60, 120);
		graphics2D.drawString("Total Followers", 60, 160);

		// Do some drawing.
		Shape circle = new Ellipse2D.Double(0, 0, 50, 50);
		graphics2D.setPaint(Color.red);
		graphics2D.fill(circle);
		graphics2D.translate(60, 0);
		circle = new Ellipse2D.Double(0, 0, 40, 40);
		graphics2D.setPaint(Color.BLACK);
		graphics2D.fill(circle);
		graphics2D.translate(60, 0);
//		graphics2D.setPaint(Color.blue);
//		graphics2D.fill(circle);

		graphics2D.setSVGCanvasSize(new Dimension(180, 150));

		// Populate the document root with the generated SVG content.
		Element root = doc.getDocumentElement();
		graphics2D.getRoot(root);




		// Display the document.
		JSVGCanvas canvas = new JSVGCanvas();
		JFrame jFrame = new JFrame();
		jFrame.getContentPane().add(canvas);
		canvas.setSVGDocument(doc);
		jFrame.pack();
		jFrame.setVisible(true);
	}
}
