import java.lang.Math;

public class Planet{
  public double xxPos;
  public double yyPos;
  public double xxVel;
  public double yyVel;
  public double mass;
  public String imgFileName;

  private static final double gravitationalConstant = 6.67e-11;

  public Planet(double xP, double yP, double xV,double yV, double m, String img){
    xxPos = xP;
    yyPos = yP;
    xxVel = xV;
    yyVel = yV;
    mass = m;
    imgFileName = img;
  }
  
  public Planet(Planet b){
    xxPos = b.xxPos;
    yyPos = b.yyPos;
    xxVel = b.xxVel;
    yyVel = b.yyVel;
    mass = b.mass;
    imgFileName = b.imgFileName;
  }

  public double calcDistance(Planet b){
    return Math.sqrt(Math.pow(this.xxPos-b.xxPos, 2) + Math.pow(this.yyPos-b.yyPos, 2));
  }

  public double calcForceExertedBy(Planet b){
    return Planet.gravitationalConstant * this.mass * b.mass / Math.pow(this.calcDistance(b), 2);
  }

  public double calcForceExertedByX(Planet b){
    return this.calcForceExertedBy(b) *  (b.xxPos-this.xxPos)/ this.calcDistance(b);
  }

  public double calcForceExertedByY(Planet b){
    return this.calcForceExertedBy(b) *  (b.yyPos-this.yyPos)/ this.calcDistance(b);
  }

  public double calcNetForceExertedByX(Planet[] allBodies){
    double netForceExertedByX = 0;

    for(Planet body : allBodies){
      if(!this.equals(body))
        netForceExertedByX+=this.calcForceExertedByX(body);
    }

    return netForceExertedByX;
  }

  public double calcNetForceExertedByY(Planet[] allBodies){
    double netForceExertedByY = 0;

    for(Planet body : allBodies){
      if(!this.equals(body))
        netForceExertedByY+=this.calcForceExertedByY(body);
    }

    return netForceExertedByY;
  }

  public void update(double dt, double fX, double fY){
    double netAccelerationX = fX/this.mass;
    double netAccelerationY = fY/this.mass;

    this.xxVel+=dt*netAccelerationX;
    this.yyVel+=dt*netAccelerationY;

    this.xxPos+=dt*(this.xxVel);
    this.yyPos+=dt*(this.yyVel);
  }

  public void draw(){
    StdDraw.picture(this.xxPos, this.yyPos, "./images/"+this.imgFileName);
  }
}