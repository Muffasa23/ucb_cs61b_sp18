public class NBody{
  public static double readRadius(String fileName){
    In in = new In(fileName);

    int numOfPlanets = in.readInt();
    double radiusOfUniverse = in.readDouble();

    return radiusOfUniverse;
  }

  public static Planet[] readPlanets(String fileName){
    In in = new In(fileName);

    int numOfPlanets = in.readInt();
    double radiusOfUniverse = in.readDouble();

    Planet[] bodies = new Planet[numOfPlanets];

    for(int i=0;i<numOfPlanets;i++){
      double xxPos = in.readDouble();
      double yyPos = in.readDouble();
      double xxVel = in.readDouble();
      double yyVel = in.readDouble();
      double mass = in.readDouble();
      String imgFileName = in.readString();

      bodies[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
    }

    return bodies;
  }

  public static void main(String[] args){
    double t=Double.parseDouble(args[0]), dt=Double.parseDouble(args[1]);
    String filename = args[2];

    double universeRadius = NBody.readRadius(filename);
    Planet[] bodies = NBody.readPlanets(filename);

    StdDraw.enableDoubleBuffering();
    StdDraw.setScale(-1*universeRadius, universeRadius);
    StdDraw.clear();

    double timer=0;
    while(timer<=t){
      double [] xForces = new double [bodies.length];
      double [] yForces = new double [bodies.length];
      
      for(int i=0;i<bodies.length;i++){
        xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
        yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
      }

      for(int i=0;i<bodies.length;i++){
        bodies[i].update(dt, xForces[i], yForces[i]);
      }

      StdDraw.picture(0, 0, "./images/starfield.jpg");

      for(int i=0;i<bodies.length;i++){
        bodies[i].draw();
      }

      StdDraw.show();
      StdDraw.pause(10);

      timer+=dt;
    }


    StdOut.printf("%d\n", bodies.length);
    StdOut.printf("%.2e\n", universeRadius);
    for(int i=0;i<bodies.length;i++){
      StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", 
                  bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                  bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);

    }
  }
}