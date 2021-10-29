package hw12.family.Controller;

import hw12.family.People.Family;
import hw12.family.People.Human;
import hw12.family.exceptions.IncorrectChoiceException;
import hw12.family.service.Services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class FamilyController {
    public Services FamilyService;
    public Menu menu;

    public FamilyController(Services service, Menu menu) {
        this.FamilyService = service;
        this.menu = menu;
    }

    public void doControl() {
        Menu menu = new Menu();
        while (true) {

            menu.showMenue();
            String choice = Menu.getChoice();
            Pattern pattern = Pattern.compile("q|exit|quit|учше|йгше", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(choice);
            if (matcher.find()) {
                System.out.println("you've quit from application!");
                break;
            }
            boolean skipFlag = menu.actionConfirmation(choice);
            if (!skipFlag) {
                continue;
            }
            try {
                processRequests(choice);
            } catch (IncorrectChoiceException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void createDefaultFamilies() {

        //исходник с данными по создаваемым семьям:
        List<List<String>> familyData = new ArrayList<>(Arrays.asList(
                Arrays.asList("Leontiy", "Zoya", "Fedotov"),
                Arrays.asList("Petya", "Natasha", "Gandzapety"),
                Arrays.asList("Grigory", "Liza", "Morarik"),
                Arrays.asList("Anton", "Olya", "Glory"),
                Arrays.asList("Pavel", "Tanya", "Primetime"),
                Arrays.asList("Viktor", "Lyuba", "Wonder"),
                Arrays.asList("Marik", "Katya", "Pendik"),
                Arrays.asList("Svyatoslav", "Tonya", "Krutovar"),
                Arrays.asList("Vladik", "Lena", "Mirolyub"),
                Arrays.asList("Sergey", "Nadya", "Rudakov"),
                Arrays.asList("Maks", "Rebekka", "Petrik"),
                Arrays.asList("Vladymir", "Nyura", "Levinsky"),
                Arrays.asList("Yura", "Klavdia", "Shifer"),
                Arrays.asList("Taras", "Marta", "Mirolyub"),
                Arrays.asList("Vovan", "Galya", "Gagik"),
                Arrays.asList("Vertal", "Alla", "Pendiks")
        )
        );

        //задание прочих входных параметров для создания семей:
        Random rnd = new Random();
        int amntOwn, amntAdopted;
        LocalDate dadBirthDate, momBirthDate;

        for (List<String> names : familyData) {
            amntOwn = rnd.nextInt(4);
            amntAdopted = rnd.nextInt(3);
            dadBirthDate = LocalDate.of(1973, 5, 13);
            momBirthDate = dadBirthDate.plusYears(10L).plusMonths(2L).plusDays(13);

            //создание семей и испытании метода createNewFamily():
            FamilyService.createNewFamily(names.get(0), names.get(1), names.get(2),
                    dadBirthDate, momBirthDate, amntOwn, amntAdopted);
        }
    }
    private int getAmtOfMembers() {
        System.out.println("Input number of family members: ");
        return parseInt(hw12.family.service.FamilyService.getKeyboardInput());
    }


    public void processRequests(String choice) throws IncorrectChoiceException {
        switch (choice) {
            case "1":
                try {
                    FamilyService.saveData();
                }
                catch (NotSerializableException e) {
                    System.out.println("saveData from file Error");
                    System.out.println("both Family and Human need to implement Serializable");
                } catch (IOException e) {
                    System.out.println("saveData from file Error");
                    System.out.println("IOException");
                } catch (Exception e) {
                    System.out.println("saveData to file Error");
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                } finally {
                }
                break;
            case "2":
                try {
                    FamilyService.loadData();
                }catch (NotSerializableException e) {
                    System.out.println("loadData from file Error");
                    System.out.println("both Family and Human need to implement Serializable");
                } catch (IOException e) {
                    System.out.println("IOException");
                    System.out.println("loadData from file Error");
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("loadData from file Error");
                    e.printStackTrace();
                } finally {
                }
                break;
            case "3":
                createDefaultFamilies();
                break;
            case "4":
                FamilyService.displayAllFamilies();
                break;
            case "5":
                FamilyService.getFamiliesBiggerThan(getAmtOfMembers());
                break;
            case "6":
                FamilyService.getFamiliesLessThan(getAmtOfMembers());
                break;
            case "7":
                int searchedMembers = getAmtOfMembers();
                int amntOfFamiliesFound = FamilyService.countFamiliesWithMemberNumber(searchedMembers);
                System.out.println("\t\t\tWe've found " + amntOfFamiliesFound
                        + " families with " + searchedMembers + " family members in them");
                break;
            case "8":
                Menu subMenu = new Menu();
                System.out.println("creating mother:");
                subMenu.getOneFamilyMemberInputDetails();
                Human mom = null;
                try {
                    mom = FamilyService.createMember(subMenu.params, "mom");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Error: mother has not been created");
                }
                System.out.println("creating father:");
                subMenu.getOneFamilyMemberInputDetails();
                Human dad = null;
                try {
                    dad = FamilyService.createMember(subMenu.params, "dad");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Error: father has not been created");
                }
            if (dad != null && mom != null) {
                Family newFam = new Family(dad, mom);
                dad.setFamily(newFam);
                mom.setFamily(newFam);
                FamilyService.saveFam(newFam);
            } else System.out.println("ERROR:  this family has NOT been created, provide correct input data!");

                break;
            case "9":
                System.out.println("Input index of family being deleted:");
                int familiesLeft = FamilyService.count() - 1;
                System.out.println("available range: from 0 to " + (familiesLeft));
                int famIndex = parseInt(hw12.family.service.FamilyService.getKeyboardInput());
                if (!FamilyService.checkInputInt(famIndex, familiesLeft)) {break;}
                FamilyService.deleteFamilyByIndex(famIndex);
                break;
            case "10":
                  String sChoice = menu.invokeSubmenu();
                  processSubmenu(sChoice);
                    break;
            case "11":
                System.out.println("Input age of children being deleted:");
                int age = parseInt(hw12.family.service.FamilyService.getKeyboardInput());
            try{
                FamilyService.deleteAllChildrenOlderThen(age);
            }catch(Exception e){
                System.out.println("error in deleteAllChildrenOlderThen");
            }
                break;
            case "12":
                System.out.println(FamilyService.count());
                break;
            default:
        }

    }

    private void processSubmenu(String choice) {
        switch(choice){
            case "1":
                System.out.println("registering birth of a child->");
                int birthFamIndex = FamilyService.getIndex("Input index of family:");
                if (birthFamIndex < 0) {break;}
                System.out.println("provide boy's name(оставь пустым и жми ввод, если будет девочка!");
                String bName = hw12.family.service.FamilyService.getKeyboardInput();
                System.out.println("provide girl's name (если выше задал имя мальчику- жми ввод, без имени девочки!)");
                String gName = hw12.family.service.FamilyService.getKeyboardInput();
                FamilyService.register(birthFamIndex, bName, gName);
                break;
            case "2":
                System.out.println("adopting a child->");
                int adoptFamIndex = FamilyService.getIndex("Input index of family:");
                if (adoptFamIndex < 0) {break;}
                System.out.println("provide name:");
                String name = hw12.family.service.FamilyService.getKeyboardInput();
                System.out.println("provide surname:");
                String surname = hw12.family.service.FamilyService.getKeyboardInput();
                System.out.println("provide sex (format:  f/m ) ->");
                String sex = hw12.family.service.FamilyService.getKeyboardInput();
                System.out.println("provide birthDate (accepted format: 'dd:mm:yyyy'):");
                String birthDateRaw = hw12.family.service.FamilyService.getKeyboardInput();
                System.out.println("provide iq:");
                String iq = hw12.family.service.FamilyService.getKeyboardInput();
                FamilyService.adopt(adoptFamIndex, name, surname, sex, birthDateRaw, parseInt(iq));
                break;
            case "3":
                System.out.println("exiting to main menu");
                break;
            default:
                System.out.println("idle exit to main menu");
        }
    }

}
