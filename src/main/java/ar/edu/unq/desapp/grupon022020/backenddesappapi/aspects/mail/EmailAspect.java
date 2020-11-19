package ar.edu.unq.desapp.grupon022020.backenddesappapi.aspects.mail;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.aspects.log.LogEndpointAspect;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.SendEmailService;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@EnableAspectJAutoProxy
public class EmailAspect {

    @Autowired
    SendEmailService emailService;
    @Autowired
    UserService userService;

    Logger logger = LoggerFactory.getLogger(LogEndpointAspect.class);

    @Pointcut("execution(* ar.edu.unq.desapp.grupon022020.backenddesappapi.service.SchedulerService.generateRankings())")
    public void methodStarterServicePointcut(){

    }
    @Before("methodStarterServicePointcut()")
    public void mailReturnedDonationBefore() {
        logger.warn("Rankings will be generated for current day!!!");
    }

    @After("methodStarterServicePointcut()")
    public void mailRankings() throws Throwable {
        logger.warn("Rankings will be sent to all donors!!!");
        List<Donor> donorList = userService.findAll();
        for (Donor donor: donorList)
        {
            emailService.sendMessageWithAttachment(donor.getMail(),
                    "Rankings del " + LocalDate.now().toString(),
                    "Hola " + donor.getName() + ", ya estan los rankings del dia " + LocalDate.now().toString(),
                    "./src/main/resources/rankings.txt",
                    "Rankings del " + LocalDate.now().toString() + ".txt");
        }
    }

    @Around("@annotation(MailReturnedDonation)")
    public Object mailReturnedDonation(ProceedingJoinPoint joinPoint) {
        logger.warn("A donation has to be returned to its donor!!!");
        List<Object> arguments = Arrays.asList(joinPoint.getArgs());
        Donation donation = (Donation) arguments.get(0);
        Donor donor = (Donor) arguments.get(1);
        String email = donor.getMail();
        logger.warn("We are about to send mail to " + email + "!!!");
        emailService.sendSimpleMessage(email,
                "Se te ha devuelto una donacion!",
                "Hola " + donor.getName() + ", solo queriamos avisarte que se te ha devuelto a " +
                        "tu cuenta una donacion de $" + donation.getAmount() + " que realizaste al " +
                        "proyecto " + donation.getProjectName() + " el dia " + donation.getDate());
        return joinPoint;

    }

}
