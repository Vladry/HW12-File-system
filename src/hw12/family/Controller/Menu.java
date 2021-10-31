package hw12.family.Controller;

import hw12.family.exceptions.EmptyFieldException;
import hw12.family.exceptions.IncorrectChoiceException;
import hw12.family.service.FamilyService;

import java.util.*;

import static java.lang.Integer.parseInt;
import static java.util.Map.entry;

public class Menu {
    private final Map<String, String> unSortedMenu = new HashMap<>(
            Map.ofEntries(
                    entry("1", "save families to file"),
                    entry("2", "upload families from file"),
                    entry("3", "auto-generate families"),
                    entry("4", "show all families"),
                    entry("5", "show families greater than"),
                    entry("6", "show families smaller than"),
                    entry("7", "show families with amount of members equal"),
                    entry("8", "create new family"),
                    entry("9", "delete family by index"),
                    entry("10", "edit family by index"),
                    entry("11", "delete children older than"),
                    entry("12", "count families")
            )
    );

    //вариант задать компаратор через переопределение метода compare:
/*
    private final TreeMap<String, String> items = new TreeMap<>(new Comparator<String>(){
        @Override
        public int compare(String a, String b){
            return parseInt(a) - parseInt(b);
        }

    });
*/
    //вариант задать компаратор лямбдой:
//    private final TreeMap<String, String> items = new TreeMap<>( (a,b)->parseInt(a) - parseInt(b));

    //вариант задать компаратор, на котором настояла IDE:
    private final TreeMap<String, String> items = new TreeMap<>(Comparator.comparingInt(Integer::parseInt));

    public Menu(){
        items.putAll(unSortedMenu);
        System.out.println("sorted menu: " + items);
    }

    public Map<String, String> params = new HashMap<>(Map.of(
            "name",           "empty",
            "surname",      "empty",
            "year of birth (format: 'yyyy')",  "empty",
            "month of birth (format: 'mm')", "empty",
            "date of birth (format: 'dd')",  "empty",
            "iq",             "empty"
    ));

    public void showMenue() {
//        NavigableMap<String, String> navMenu = items.descendingMap();  //для режима reverse menu
        Iterator<Map.Entry<String, String>> iterator = items.entrySet().iterator();
        Map.Entry<String, String> entry = null;
        System.out.println("\n\nchoose an operation out of the following possible options:\n");
        while (iterator.hasNext()) {
            entry = iterator.next();
            System.out.print("choose " + entry.getKey() + " to ");
            System.out.println(entry.getValue() + ";");
        }
    }

    public void getOneFamilyMemberInputDetails(){
        System.out.println("Provide the following ->");
        for(Map.Entry<String, String> menuItem : this.params.entrySet() ) {
            System.out.println("input " + menuItem.getKey() + ": ");
            String memberFeature = null;
            try {
                memberFeature = FamilyService.getKeyboardInput();
            } catch (EmptyFieldException e) {
                System.out.println(e.getMessage());
            }
            menuItem.setValue(memberFeature);
        };
    }

    public static String getChoice() {
        System.out.println("\ninput your choice here:");
        return FamilyService.getKeyboardInput();
    }

    public boolean actionConfirmation(String choice) {
        String errMsg = "Please, enter a number corresponding to one of the menu items. Make sure you are inputting a number and this number does not include any other characters!";
        String action = null;
        try {
            action = items.get(choice);
            if (action == null) throw new IncorrectChoiceException(errMsg);
                System.out.print("you've chosen: " + choice + ". Now, we'll ");
                System.out.println(action);

        } catch (IncorrectChoiceException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public String invokeSubmenu(){
        System.out.println("you've entered child processing section, select operation:");
        System.out.println("1 - to register new born child");
        System.out.println("2 - to adopt a child");
        System.out.println("3 - exit");
        String answer = FamilyService.getKeyboardInput();
        try {
            parseInt(answer);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input in menu item 8. " +
                    "\nPlease, enter menu item 8 again and provide a correct answer. Make sure you are inputting a number!");
        }
        return answer;
    }

}
