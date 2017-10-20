package auto.master.service.autho;

public interface IAuthoService {

	String getUploadIp(String accessToken);

	String getImageToken(String ip);

}
