package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleServerController {
	
	@RequestMapping("/")
	public String defaultPage() {
		StringBuilder sb = new StringBuilder();
		File directory = new File(".");
		File[] fList = directory.listFiles();
		sb.append("<html><body><ul>");
		for (File f : fList) {
			String fName = f.isDirectory()? f.getName() + "/" : f.getName();
			sb.append("<li><a href=\"");
			sb.append(fName);
			sb.append("\">");
			sb.append(fName);
			sb.append("</li>");
		}
		sb.append("</ul></body></html>");
		return sb.toString();
		//return "hello";
	}
	
	
	
	@RequestMapping("/{value:.+}")
	public String getPathVar(@PathVariable(value="value") String val) throws IOException {
		if (val.equals("")) return "at root dir";
		StringBuilder sb = new StringBuilder();
		System.out.println(val);
		File handle = new File(val);
		if (handle.isFile()) {
			StringBuilder fileContents = new StringBuilder();
			BufferedReader br = new BufferedReader(
					new FileReader(val));
			String line;
			while((line = br.readLine()) != null)
			{
			    fileContents.append(line);
			}
			br.close();
			return fileContents.toString();
		} else {
			File[] fList = handle.listFiles();
			sb.append("<html><body><ul>");
			for (File f : fList) {
				String fName = f.isDirectory()? f.getName() + "/" : f.getName();
				sb.append("<li><a href=\"");
				sb.append(fName);
				sb.append("\">");
				sb.append(fName);
				sb.append("</li>");
			}
			sb.append("</ul></body></html>");
			return "Directory listing for: " + val + "<hr>" + sb.toString();
		}
	}

}
