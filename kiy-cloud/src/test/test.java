package test;

import java.io.IOException;
import java.util.UUID;

public class test {
	public static void main(String[] args) throws IOException {
		// 读取本地的语音文件
//		Path path = FileSystems.getDefault().getPath("C:\\Users\\Administrator\\Desktop\\kiy-home\\kiy-cloud\\sample.pcm");
//		byte[] data = Files.readAllBytes(path);
//		HttpResponse response = HttpUtil.sendAsrPost(data, "pcm", 16000, Config.RECOGNIZE_URL, Config.RECOGNIZE_ACCESS_KEY_ID, Config.RECOGNIZE_ACCESS_KEY_SECRET);
//		String result = response.getResult();
//		System.err.println(result);
//		System.err.println(JSON.toJSONString(response));
		
//		String filename = UUID.randomUUID().toString().replace("-", "");
//		
//		HttpResponse sendTtsPost = HttpUtil.sendTtsPost("测试的样例", filename);
//		String filePath = sendTtsPost.getFilePath();
//		System.err.println(filePath);
//		Config.load();
//		Config.check();
//		Data.initialize();
//		
//		ServoDao servoDao = new ServoDaoImpl();
//		servoDao.updateServoIp("3f46451e-7bf5-22e7-bb87-4061866d97d0", "192.168.0.214");
//		
		System.err.println(UUID.randomUUID().toString());
	}
}
