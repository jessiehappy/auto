package auto.master.schedule.job;

import org.springframework.stereotype.Component;

import auto.common.schedule.SimpleJob;

@Component
public class TestJob implements SimpleJob{

	@Override
	public String getQuartzTime() {
		// TODO Auto-generated method stub
		return "30 1/9 * * * ?";
	}

	@Override
	public void run() {
		System.out.println("test job run");
	}

}
