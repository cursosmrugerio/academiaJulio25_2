package com.curso.v0;

class Building { }

public class Bam extends Building {
    public static void main(String[] args) {
        Building build1 = new Building();   
        Bam bam1 = new Bam();
        Bam bam2 = (Bam) build1;            // compiles; may throw at runtime if build1 is not a Bam
        Object obj1 = (Object) build1;
        //String str1 = (String) build1;   // <-- removed (compile-time error)
        Building build2 = (Building) build1;
        
        Building bd = (Building)bam1;
        
        System.out.println("Fin");
    }
}
