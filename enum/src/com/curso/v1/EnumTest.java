package com.curso.v1;

public class EnumTest {
	
	enum Day {  
		MONDAY("Baja"){
			void getTemperatura() {
			}
		}, 
		TUESDAY("Baja"){
			void getTemperatura() {
			}
		}, 
		WEDNESDAY("Baja"){
			void getTemperatura() {
			}
		},  
		THURSDAY("Media"){
			void getTemperatura() {
			}
		},  
		FRIDAY("Media"){
			void getTemperatura() {
			}
		}, 
		SATURDAY("Alta"){
			void getTemperatura() {
			}
		},  
		SUNDAY("Alta"){
			void getTemperatura() {
			}
		};
		
		private String visitantes;
		
		Day(String visitantes){
			this.visitantes = visitantes;
		}
		
		String getVisitantes() {
			return visitantes;
		}
		
		static void saludar() {
			System.out.println("Hello");
		}
		
		abstract void getTemperatura();
		
	}

	public static void main(String[] args) {
		Day.saludar();
		for (Day day : Day.values()) {
			System.out.println(day + " "+ day.ordinal());
			System.out.println(day.getVisitantes());
		}
	}
}
