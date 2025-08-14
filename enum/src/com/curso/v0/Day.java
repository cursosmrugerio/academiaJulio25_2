package com.curso.v0;

public final class Day {

    private final String name;
    private final int ordinal;

    // Array para guardar todas las constantes
    private static final Day[] VALUES;

    // Constantes (equivalentes a enum)
    public static final Day MONDAY    = new Day("MONDAY", 0);
    public static final Day TUESDAY   = new Day("TUESDAY", 1);
    public static final Day WEDNESDAY = new Day("WEDNESDAY", 2);
    public static final Day THURSDAY  = new Day("THURSDAY", 3);
    public static final Day FRIDAY    = new Day("FRIDAY", 4);
    public static final Day SATURDAY  = new Day("SATURDAY", 5);
    public static final Day SUNDAY    = new Day("SUNDAY", 6);

    // Constructor privado
    private Day(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    static {
        VALUES = new Day[]{
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY,
            FRIDAY, SATURDAY, SUNDAY
        };
    }

    public String name() {
        return name;
    }

    public int ordinal() {
        return ordinal;
    }

    public static Day[] values() {
        // Clonamos para evitar modificaciones externas
        return (Day[]) VALUES.clone();
    }

    public static Day valueOf(String name) {
        if (name == null) throw new NullPointerException("Name is null");
        for (int i = 0; i < VALUES.length; i++) {
            if (VALUES[i].name.equals(name)) {
                return VALUES[i];
            }
        }
        throw new IllegalArgumentException("No enum constant Day." + name);
    }

    public String toString() {
        return name;
    }
}
