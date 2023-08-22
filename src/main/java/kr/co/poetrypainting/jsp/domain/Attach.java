package kr.co.poetrypainting.jsp.domain;

import java.io.File;

import kr.co.poetrypainting.jsp.util.paramSolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attach {
	private String uuid;
	private String origin;
	private boolean image;
	private String path;
	private Long bno;
	
	public Attach(String uuid, String origin, boolean image, String path) {
		super();
		this.uuid = uuid;
		this.origin = origin;
		this.image = image;
		this.path = path;
	}
	public String getQueryString() {
		return String.format("%s=%s&%s=%s&%s=%s", "uuid",uuid,"origin",origin,"path",path);
	}
	public File getFile() {
		
		return getFile(false);
	}
	public File getFile (boolean thumb) {
		File file = null;
		
		file = new File(paramSolver.UPLOAD_PATH, path);
		// 파일명 중 마지막 .의 위치
		
		int dotIdx = origin.lastIndexOf(".");

		// 확장자를 담을 변수
		String ext = "";

		// 확장자 구하기
		if (dotIdx > -1) {
			ext = origin.substring(dotIdx); // .txt

		}
		file = new File(file, uuid + (thumb ? "_t" : "") + ext);
		return file;
	}
}

