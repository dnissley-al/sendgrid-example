package com.example;

import com.sendgrid.*;
import java.io.IOException;

public class Example {
  public static void main(String[] args) throws IOException {
    //sendSimpleEmail();
    //getTemplate();
    //getAllTemplates();
    //sendTemplatedEmail();
    sendCustomTemplatedEmail();
  }

  public static void sendCustomTemplatedEmail() throws IOException {
    Email from = new Email("test@example.com");
    String subject = "Test Subject {{name}} {{city}}";
    Email to = new Email("dylan.nissley@gmail.com");
    Content content = new Content("text/html", "<strong>Name: {{name}}</strong><br>City: {{city}}asdf");
    Mail mail = new Mail(from, subject, to, content);
    mail.personalization.get(0).addSubstitution("{{name}}", "Example User");
    mail.personalization.get(0).addSubstitution("{{city}}", "Denver");

    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException ex) {
      throw ex;
    }   
  }

  public static void sendTemplatedEmail() throws IOException {
    Email from = new Email("test@example.com");
    Email to = new Email("dylan.nissley@gmail.com");

    Mail mail = new Mail();

    DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();

    personalization.addTo(to);
    personalization.addDynamicTemplateData("firstName", "Dylan");
    personalization.addDynamicTemplateData("lastName", "Nissley");
    personalization.addDynamicTemplateData("membershipLevel", "Gold");
    personalization.addDynamicTemplateData("infoLink", "https://www.google.com");

    mail.setFrom(from);
    mail.addPersonalization(personalization);
    mail.setTemplateId("d-4c01c7d9c28a4349a3d4d51230693848");

    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println("Email sent successfully, here's the details:");
      System.out.println("  Status code: " + response.getStatusCode());
      System.out.println("  Response body: " + response.getBody());
      System.out.println("  Response headers: " + response.getHeaders());
    } catch (IOException ex) {
      throw ex;
    }    
  }

  public static void getTemplate() throws IOException {
    try {
      SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
      Request request = new Request();
      request.setMethod(Method.GET);
      request.setEndpoint("templates/d-4c01c7d9c28a4349a3d4d51230693848");
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException ex) {
      throw ex;
    }
  }

  public static void getAllTemplates() throws IOException {
    try {
      SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
      Request request = new Request();
      request.setMethod(Method.GET);
      request.setEndpoint("templates");
      request.addQueryParam("generations", "dynamic");
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getBody());
      System.out.println(response.getHeaders());
    } catch (IOException ex) {
      throw ex;
    }
  }

  public static void sendSimpleEmail() throws IOException {
    System.out.println("Starting simple email example.");

    System.out.println("Constructing simple email using sendgrid library...");
    Email from = new Email("test@example.com");
    String subject = "Sending with SendGrid is Fun";
    Email to = new Email("dylan.nissley@gmail.com");
    Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
    Mail mail = new Mail(from, subject, to, content);
    System.out.println("Done.");

    System.out.println("Collecting api key from environment variable SENDGRID_API_KEY...");
    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
    System.out.println("Got it.");

    System.out.println("Setting up a request to sendgrid...");
    Request request = new Request();
    System.out.println("Done.");

    System.out.println("Attempting to send email...");
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println("Email sent successfully, here's the details:");
      System.out.println("  Status code: " + response.getStatusCode());
      System.out.println("  Response body: " + response.getBody());
      System.out.println("  Response headers: " + response.getHeaders());
    } catch (IOException ex) {
      throw ex;
    }
  }
}

