package hw12.family;

import hw12.family.Controller.FamilyController;
import hw12.family.Controller.Menu;
import hw12.family.FamilyDAO.CollectionFamilyDao;
import hw12.family.service.FamilyService;

public class Main {
    public static void main(String[] args) {

        //создание сервиса DAO:
        CollectionFamilyDao familyMemStorage = new CollectionFamilyDao();//создаём хранилище1
        FamilyService service = new FamilyService(familyMemStorage);
        Menu menu = new Menu();
        FamilyController controller = new FamilyController(service, menu);

        controller.doControl();
    }
}



