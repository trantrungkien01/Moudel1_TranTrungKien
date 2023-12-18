package moudel;

import javax.crypto.spec.PSource;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;

import static moudel.Library.*;

public class Category implements IEntity, Serializable {
    private static final long serialVersionUID = 26177362790879650L;
    private int id;
    private String name;
    private Boolean status;

    public Category() {
    }

    public Category(int id, String name, Boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public void input(Scanner scanner) {
        this.id = checkId(scanner);
        this.name = checkName(scanner);
        this.status = checkStatus(scanner);
        Category category = new Category(this.id, this.name, this.status);
        listCategory.add(category);

    }

    public int checkId(Scanner scanner) {
        listCategory = Category.readDataToFile();
        do {
            System.out.println("Nhập vào ID: ");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                boolean checkOut = false;
                if (id < 0) {
                    System.err.println("Nhập mã lớn hơn 0");
                } else {
                    for (int i = 0; i < listCategory.size(); i++) {
                        if (listCategory.get(i).getId() == id) {
                            checkOut = true;// đã xuất hiện mã này
                            break;
                        }
                    }
                }
                if (checkOut == true) {
                    System.err.println("Đã có mã này");
                } else {
                    return id;
                }
            } catch (Exception ex) {
                System.err.println("Lỗi khi nhập ID");
            }
        } while (true);
    }

    public String checkName(Scanner scanner) {
        listCategory = Category.readDataToFile();
        boolean checkOut = false;// chưa xuất hiên tên trùng lặp
        do {
            System.out.println("Nhập vào tên thê loại: ");
            try {
                String name = scanner.nextLine();
                if (name.length() >= 6 && name.length() <= 30) {
                    for (int i = 0; i < listCategory.size(); i++) {
                        if (listCategory.get(i).getName().equals(name)) {
                            checkOut = true;// đã xuất hiện tên trùng lặp
                            break;
                        }
                    }
                    if (checkOut == true) {
                        System.err.println("Đã tồn tại tên thể loại này");
                    } else {
                        return name;
                    }

                } else {
                    System.err.println("Tên thể loại từ 6 -> 30 ký tự");
                }
            } catch (Exception ex) {
                System.err.println("Lỗi khi nhập vào tên thể loại");
            }
        } while (checkOut);
        return null;
    }

    public boolean checkStatus(Scanner scanner) {
        do {
            System.out.println("Nhập vào trạng thái: ");
            try {
                String status = scanner.nextLine();
                if (status.equals("true") || status.equals("false")) {
                    return Boolean.parseBoolean(status);
                } else {
                    System.err.println("Trạng thái chỉ nhận true | false");
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi nhập vào trạng thái thể loại");
            }
        } while (true);
    }

    public void sortCategoryName() {
        listCategory = Category.readDataToFile();
        if (listCategory.isEmpty()) {
            System.err.println("Danh sách đang rỗng");
            return;
        }
        listCategory.stream().sorted(Comparator.comparing(Category::getName)).forEach(Category::ouput);
    }

    public void updateById(Scanner scanner) {
        listCategory = Category.readDataToFile();
        System.out.println("Nhập vào ID cần cập nhật: ");
        try {
            int idUpdate = Integer.parseInt(scanner.nextLine());
            if (idUpdate <= 0) {
                System.err.println("Nhập lại ID lớn hơn 0");
            } else {
                for (int i = 0; i < listCategory.size(); i++) {
                    if (idUpdate == listCategory.get(i).getId()) {
                        boolean check = true;
                        do {
                            System.out.println("********* Menu Cập Nhật***************");
                            System.out.println("1. Cập nhật lại tên");
                            System.out.println("2. Cập nhật lại trạng thái");
                            System.out.println("3. Thoát cập nhật");
                            System.out.println("Lựa chọn của bạn: ");
                            try {
                                int choice = Integer.parseInt(scanner.nextLine());
                                switch (choice) {
                                    case 1:
                                        do {
                                            System.out.println("Nhập vào tên mới: ");
                                            try {
                                                String newName = scanner.nextLine();
                                                if (newName.trim().isEmpty()) {
                                                    System.err.println("Không được để trống tên thể loại");
                                                } else if (newName.trim().length() >= 6 && newName.trim().length() <= 30) {
                                                    for (int j = 0; j < listCategory.size(); j++) {
                                                        if (listCategory.get(j).getName().equals(newName)) {
                                                            System.err.println("Đã có tên này trong danh sách");
                                                        }
                                                        break;
                                                    }
                                                        listCategory.get(i).setName(newName);
                                                        Category.writeDataToFile(listCategory);
                                                    System.err.println("Đã cập nhật tên thành công");
                                                        break;
                                                } else {
                                                    System.err.println("Tên phải từ 6 -> 30 ký tự");
                                                }
                                            } catch (Exception ex) {
                                                System.err.println("Lỗi khi nhập tên mới");
                                            }
                                            for (int k = 0; k < listCategory.size(); k++) {
                                                listCategory.get(k).ouput();
                                            }
                                        } while (true);
                                    case 2:
                                        break;
                                    case 3:
                                        check = false;
                                        break;
                                    default:
                                        System.out.println(" Hãy chọn từ 1- > 3");
                                }
                            } catch (Exception ex) {
                                System.err.println("Lỗi khi nhập lựa chọn");
                            }
                        } while (check);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Có lỗi khi nhập ID cập nhật");
        }
    }
    public void count(){
        listCategory=Category.readDataToFile();
        listbooks=Book.readDataToFile();
        int count=0;
        for(int i=0;i<listCategory.size();i++)
        {
            int countIndex=listCategory.get(i).getId();
            for(int j=0;j<listbooks.size();j++)
            {
                if(countIndex==listbooks.get(j).getCategoryId())
                {
                    count+=1;
                }
            }
            System.out.println(" "+listCategory.get(i).getName()+" : "+count);
            count=0;
        }
    }

    public void deleteByIdCategory(Scanner scanner) {
        listCategory = Category.readDataToFile();
        System.out.println("Nhập vào ID cần xóa( mã thể loại ): ");
        try {
            int deleteId = Integer.parseInt(scanner.nextLine());
            for (int index = 0; index < listCategory.size(); index++) {
                if (listCategory.get(index).getId() == deleteId) {
                    listCategory.remove(index);
                    System.err.println("Đã xóa thành công");
                    Category.writeDataToFile(listCategory);
                    return;
                }
            }
            System.err.println("ID không tồn tại");
        } catch (Exception ex) {
            System.err.println("Lỗi nhập vào ID ");
        }

    }

    @Override
    public void ouput() {
        System.out.printf("|  %-10d   |     %-15s     |  %-10b   |\n", this.id, this.name, this.status);
        System.out.printf("----------+---------------+--------------------------+----\n");
    }


    public String toString() {
        return "Danh sách thể loại: " + "Mã thể loại = " + id + ", Tên thể loại = " + name + ", Trạng thái thể loại = " + status;


    }

    public void updateData(Scanner scanner) {
        boolean isExitUpdate = true;
        do {
            System.out.println("Câp nhât thông tin");
            System.out.println("1. Cập nhât id");
            System.out.println("2. Cập nhât name");
            System.out.println("3. Cập nhât status");
            System.out.println("4. Thoát");
            System.out.println("Lựa chon của bạn:");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        //  this.id = inputId(scanner);
                        break;
                    case 2:
                        //    this.name = inputName(scanner);
                        break;
                    case 3:
                        //  this.status = inputStatus(scanner);
                        break;
                    case 4:
                        isExitUpdate = false;
                        break;
                    default:
                        System.out.println("nhập lựa chọn từ 1-5");
                }
            } catch (NumberFormatException e) {
                System.err.println("vui lòng nhâp số nguyên!");

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

        } while (isExitUpdate);
    }


    public static void writeDataToFile(List<Category> categoryList) {
        // Đọc file
        File file = new File("category.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(categoryList);
            oos.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static List<Category> readDataToFile() {
        List<Category> categoryListRead = null;
        File file = new File("category.txt");
        if (!file.exists()) {// nếu file đang rôỗng
            return new ArrayList<>();
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            categoryListRead = (List<Category>) ois.readObject();
            return categoryListRead;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }


}



