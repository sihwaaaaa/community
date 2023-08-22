package kr.co.poetrypainting.jsp.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import kr.co.poetrypainting.jsp.domain.Attach;
import kr.co.poetrypainting.jsp.util.paramSolver;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;

@MultipartConfig(location = paramSolver.UPLOAD_PATH, fileSizeThreshold = 1024)
@WebServlet("/fileUpload")
public class FileUploadController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("file.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getContentType());

		Collection<Part> parts = req.getParts();

		List<Attach> attachs = new ArrayList<>();
		for (Part p : parts) {
			if (p.getContentType() == null) {
				continue;
			}
			// file
			// 파일의 원본이름
			String origin = p.getSubmittedFileName();

			// 파일명 중 마지막 .의 위치
			int dotIdx = origin.lastIndexOf(".");

			// 확장자를 담을 변수
			String ext = "";

			// 확장자 구하기
			if (dotIdx > -1) {
				ext = origin.substring(dotIdx); // .txt

			}

			// uuid문자열 생성
			String uuid = UUID.randomUUID().toString();

			// thumbnail
			// c:/upload/2023/03/14/uuid_t.ext

			// 경로 문자열 반환
			String path = getTodayStr();

			// 경로 문자열에 대한 폴더 생성
			File targetPath = new File(paramSolver.UPLOAD_PATH, path);
			if (!targetPath.exists()) {
				targetPath.mkdirs();
			}

			// 원본에 대한 저장
			File fs = new File(targetPath, uuid + ext);
			p.write(fs.getPath());

			// 이미지 여부 확인
			System.out.println(p.getContentType());
//				List<String> exceptImgMimes = Arrays.asList("image/x-icon", "image/webp");
			boolean image = p.getContentType().startsWith("image"); // && !exceptImgMimes.contains(p.getContentType());
			if (image) {
				// 섬네일 생성
				try {
					File out = new File(targetPath, uuid + "_t" + ext);
//					Files.copy(file.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING);
					Thumbnailator.createThumbnail(fs, out, 200, 200);

				} catch (UnsupportedFormatException ignore) {
					// TODO: handle exception
				}

			}
			attachs.add(new Attach(uuid, origin, image, path));
			// uuid,origin,image,path

			attachs.forEach(System.out::println);
		}
	}

	private String getTodayStr() {
		return new SimpleDateFormat("yyy/MM/dd").format(System.currentTimeMillis());
	}

}
