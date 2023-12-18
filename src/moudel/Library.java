package moudel;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Library {
    public static List<Category> listCategory = new ArrayList<>();
    public static List<Book> listbooks = new ArrayList<>();

    public static void main(String[] args) {
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println(" ========== QUẢN LÝ THƯ VIỆN ========== ");
            System.out.println("1. Quản lý Thể loại");
            System.out.println("2. Quản lý Sách");
            System.out.println("3. Thoát");
            System.out.print("Nhập lựa chọn của bạn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    catalogMenu(scanner);
                    break;
                case 2:
                    manageBooks(scanner);
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Mời nhập từ 1 đến 3!");
                    break;
            }
        } while (true);
    }

    public static void catalogMenu(Scanner scanner) {
        Category category = new Category();
        boolean isExit = true;
        do {
            System.out.println(" ========== Quản Lý Thể Loại ========== ");
            System.out.println("1. Thêm mới thể loại");
            System.out.println("2. Hiện thị danh sách theo tên A - Z");
            System.out.println("3. Thống kê thể loại và số sách có trong mỗi thể loại");
            System.out.println("4. Cập nhập thể loại");
            System.out.println("5. Xóa thể loại");
            System.out.println("0. Hiển thị kết quả trong file");
            System.out.println("6. Quay lại");
            System.out.print("Nhập lựa chọn của bạn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Nhập vào số thể loại cần thêm: ");
                    try {
                        int n = Integer.parseInt(scanner.nextLine());
                        for (int i = 0; i < n; i++) {
                            category.input(scanner);
                            Category.writeDataToFile(listCategory);
                            System.err.println("Đã thêm vào file thành công");
                        }
                    } catch (Exception e) {
                        System.err.println("Có lỗi khi nhập vào số lượng thể loại");
                    }
                    break;
                case 2:
                    category.sortCategoryName();
                    break;
                case 3:
                    category.count();
                    break;
                case 4:
                    category.updateById(scanner);
                    break;
                case 5:
                    category.deleteByIdCategory(scanner);
                    break;
                case 0:
                    Library.listCategory = Category.readDataToFile();
                    if (Library.listCategory == null) {
                        System.err.println("Có lỗi  khi đọc file");
                    } else {
                        for (int i = 0; i < Library.listCategory.size(); i++) {
                            Library.listCategory.get(i).ouput();
                        }
                    }
                    break;
                case 6:
                    isExit = false;
                    break;
                default:
                    System.out.println("Mời nhập từ 1-5!");
                    break;
            }
        } while (isExit);
    }

    public static void manageBooks(Scanner scanner) {
        Book book = new Book();
        boolean isExit = true;
        do {
            System.out.println(" ========== QUẢN LÝ SÁCH ========== ");
            System.out.println("1. Thêm mới sách");
            System.out.println("2. Cập nhập thông tin sách");
            System.out.println("3. Xóa sách");
            System.out.println("4. Tìm kiếm sách");
            System.out.println("5. Hiện thị danh sách theo nhóm thể loại");
            System.out.println("6. Quay lại");
            System.out.println("0. Xem kết quả trong file");
            System.out.println("Nhập lựa chọn của bạn: ");
            try {
                int  choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Nhập vào số sách: ");
                        int n = Integer.parseInt(scanner.nextLine());
                        for (int i = 0; i < n; i++) {
                            book.input(scanner);
                            Book.writeDataToFile(listbooks);
                            System.err.println("Đã thêm thành công");
                        }
                        break;
                    case 2:
                        // Cập nhật thông tin sách
                       updateBook(scanner);
                        break;
                    case 3:
                        // Xóa sách
                        book.deleteBook(scanner);
                        break;
                    case 4:
                        // Tìm kiếm sách
                        searchBook(scanner);
                        break;
                    case 5:
                        // Hiển thị danh sách sách theo nhóm thể loại
                        booksCategory();
                        break;
                    case 6:
                        isExit = false;
                        break;
                    case 0:
                        Library.listbooks = Book.readDataToFile();
                        if (Library.listbooks == null) {
                            System.err.println("Có lỗi  khi đọc file");
                        } else {
                            for (int i = 0; i < Library.listbooks.size(); i++) {
                                Library.listbooks.get(i).ouput();
                            }
                        }
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ mời nhập lại");
                }
            } catch (Exception e) {
                System.err.println("có lỗi khi nhập vào lựa chọn");
            }
        } while (isExit);
    }


    public static void addCategoryName(Scanner scanner) {
        listCategory.stream().sorted(Comparator.comparing(Category::getName)).forEach(System.out::println);
    }

    public static void addStatus() {
        for (Category category : listCategory) {
            long cntCatagory = listbooks.stream().filter(book -> book.getCategoryId() == category.getId()).count();
            System.out.printf("thể loại %s có %d sản phẩm\n", category.getName(), cntCatagory);
        }
    }

    public static void sortCategoryByName() {
        listCategory.stream().sorted(Comparator.comparing(Category::getName)).forEach(System.out::println);
    }

    public static void updateCategory(Scanner scanner) {
        System.out.println("Nhập vào mã thể loại cần cập nhật:");
        int updateId = Integer.parseInt(scanner.nextLine());
        boolean isUpdate = false;
        for (Category category : listCategory) {
            if (category.getId() == updateId) {
                category.updateData(scanner);
                isUpdate = true;
                break;
            }
        }

        if (!isUpdate) {
            System.out.println("Không tồn tại mã thể loại!");
        }
    }


    public static void addBook(Scanner scanner) {
        boolean isExist = true;
        int numAdd = 0;
        System.out.println("nhập số lượng sách bạn muốn thêm:");
        do {
            try {
                numAdd = Integer.parseInt(scanner.nextLine());
                isExist = false;
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (isExist);
        for (int i = 0; i < numAdd; i++) {
            Book book = new Book();
            book.input(scanner);
        }
    }

    public static void updateBook(Scanner scanner) {
        listbooks=Book.readDataToFile();
        System.out.printf("Nhập vào mã sách cần cập nhật :");
        String updateId = scanner.nextLine();
        boolean isUpdateBook = false;
        for (Book book : listbooks) {
            if (book.getId().equals(updateId)) {
                book.updateData(scanner);
                isUpdateBook = true;
                break;
            }
        }
        if (!isUpdateBook) {
            System.out.println("Mã sách không tồn tại vui lòng nhập lại: ");
        }
    }

    public static void deleteBook(Scanner scanner) {
        System.out.println("Mã sản phẩm cần xóa:");
        String deleteId = scanner.nextLine();
        boolean isDelete = false;

        for (Book book : listbooks) {
            if (book.getId().equals(deleteId)) {
                listbooks.remove(book);
                isDelete = true;
                System.out.println("Xóa sản phẩm thành công!");
                break;
            }
        }

        if (!isDelete) {
            System.out.println("Không tồn tại mã sản phẩm!");
        }
    }

    public static void searchBook(Scanner scanner) {
        listbooks=Book.readDataToFile();
        System.out.println("Tên sản phẩm tìm kiếm:");
        String searchName = scanner.nextLine();
     //   Book.header();
       // listbooks.stream().filter(book -> book.getTitle().contains(searchName)).forEach(Book::ouput);
        for(int i=0;i<listbooks.size();i++)
        {
            if(listbooks.get(i).getTitle().equals(searchName))
            {
                listbooks.get(i).ouput();
            }
        }
    }

    public static void booksCategory() {
        listbooks = Book.readDataToFile();
        listCategory = Category.readDataToFile();
        String name = "";
        String nameBook = "";
        for (int i = 0; i < listCategory.size(); i++) {
            nameBook = listCategory.get(i).getName();
            System.out.println(" Tên sách theo thể loại: " + nameBook);
            for (int j = 0; j < listbooks.size(); j++) {
                if (listCategory.get(i).getId() == listbooks.get(j).getCategoryId()) {
                    name = listbooks.get(j).getTitle();
                    System.out.println(name);
                }
            }
        }
    }
}
