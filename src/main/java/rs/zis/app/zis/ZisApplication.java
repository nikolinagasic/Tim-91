package rs.zis.app.zis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rs.zis.app.zis.controller.ClinicAdministratorController;
import rs.zis.app.zis.domain.DoctorTerms;
import rs.zis.app.zis.domain.Room;
import rs.zis.app.zis.service.ClinicService;
import rs.zis.app.zis.service.DoctorTermsService;
import rs.zis.app.zis.service.RoomService;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ZisApplication {


	public static void main(String[] args) {
		SpringApplication.run(ZisApplication.class, args);


			}




}
