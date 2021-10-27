package hw12.family.People;

import hw12.family.Animals.Pet;
import hw12.family.DayOfWeek;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

final public class Woman extends Human {
    Woman(){
        super();
    }

    public Woman(String name, String surname, LocalDate birthDate){
        super(name, surname, birthDate);
    }
    public Woman(String name, String surname, LocalDate birthDate,
                 int iq, Map<DayOfWeek, List<String>> schedule, Family family){
        super(name, surname, birthDate, iq, schedule, family);
    }

    public Woman(String name, String surname, String birthYear, String birthMonth, String birthDay, int iq) {
        super();
        String setDate = birthDay+":"+ birthMonth +":"+birthYear;
        System.out.println("конструктор устанавливает LocalDate: " + setDate);
        this.setBirthDate(LocalDate.parse(birthDay+":"+ birthMonth +":"+birthYear,
                DateTimeFormatter.ofPattern("dd:MM:yyyy")) );
        this.setName(name);
        this.setSurname(surname);
        this.setIq(iq);
    }

    public void makeup (){
        System.out.println("Запущена утрення процедура краски");
    }

    @Override
    public void greetPet(Pet pet) {
        System.out.println("Хорошая собака, " + pet.getNickname() + ", принеси маме тапочки!");
    }
}
