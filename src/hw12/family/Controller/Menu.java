package hw12.family.Controller;

import hw12.family.IncorrectChoiceException;
import hw12.family.service.FamilyService;

import java.util.*;
import static java.util.Map.entry;

public class Menu {
    private final TreeMap<String, String> items = new TreeMap<>(
            Map.ofEntries(
                    entry("1", "auto-generate families"),
                    entry("2", "show all families"),
                    entry("3", "show families greater than"),
                    entry("4", "show families smaller than"),
                    entry("5", "show families with amount of members equal"),
                    entry("6", "create new family"),
                    entry("7", "delete family by index"),
                    entry("8", "edit family by index"),
                    entry("9", "delete children older than")
            )
    );
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
            menuItem.setValue(FamilyService.getKeyboardInput());
        };
    }

    public static String getChoice() {
        System.out.println("\ninput your choice here:");
        return FamilyService.getKeyboardInput();
    }

    public boolean actionConfirmation(String choice) {
        String errMsg = "There's no such option. Please, re-input your choice:";
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
        return FamilyService.getKeyboardInput();

    }

}
