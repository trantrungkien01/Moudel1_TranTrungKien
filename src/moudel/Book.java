package moudel;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static moudel.Library.listbooks;
import static moudel.Library.listCategory;

public class Book implements IEntity, Serializable {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private String description;
    private int categoryId;

    public Book() {
    }

    public Book(String id, String title, String author, String publisher, int year, String description, int categoryId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.description = description;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void input(Scanner scanner) {
        this.id = inputId(scanner);
        this.title = inputTitle(scanner);
        this.author = inputAuthor(scanner);
        this.publisher = inputPublisher(scanner);
        this.year = inputYear(scanner);
        this.description = inputDescription(scanner);
        this.categoryId = inputCategoryId(scanner);
        Book book = new Book(this.id, this.title, this.author, this.publisher, this.year, this.description, this.categoryId);
        listbooks.add(book);
    }

    @Override
    public void ouput() {
        System.out.println("mã sách: " + this.id);
        System.out.println("tiêu đề: " + this.title);
        System.out.println("tác giả: " + this.author);
        System.out.println("nhà xuát bản: " + this.publisher);
        System.out.println("năm xuất bản: " + this.year);
        System.out.println("mô tả: " + this.description);
        System.out.println("mã thể loại: " + this.categoryId);
    }

    public String inputId(Scanner scanner) {
        do {
            System.out.println("Mã sách (bắt đầu bằng “B”, độ dài 4 kí tự):");
            String id = scanner.nextLine();
            if (id.length() == 4) {
                if (id.startsWith("B")) {
                    boolean isIdBook = false;
                    for (Book book : listbooks) {
                        if (book.getId().equals(id)) {
                            isIdBook = true;
                            break;
                        }
                    }
                    if (isIdBook) {
                        System.out.println("Mã sách đã tồn tại! vui lòng nhập lại");
                    } else {
                        return id;
                    }
                } else {
                    System.out.println("mã sách bắt đầu là B! vui lòng nhập lại");
                }
            } else {
                System.out.println("mã sách có 4 kí tự! vui lòng nhập lại");
            }

        } while (true);
    }

    public String inputTitle(Scanner scanner) {
        do {
            System.out.println("Tiêu đề sách:");
            String title = scanner.nextLine();
            if (title.length() > 6 && title.length() < 50) {
                boolean isName = false;
                for (Book book : listbooks) {
                    if (book.getTitle().equals(title)) {
                        isName = true;
                        break;
                    }
                }

                if (isName) {
                    System.out.println("Tiêu đề sách đã tồn tại! vui lòng nhập lại");
                } else {
                    return title;
                }
            } else {
                System.out.println("Tiêu đề sách có từ 6-50 kí tự! vui lòng nhập lại");
            }
        } while (true);
    }

    public String inputAuthor(Scanner scanner) {
        do {
            System.out.println("Nhập vào tên tác giả : ");
            String author = scanner.nextLine();
            if (author.trim().isEmpty()) {
                System.out.println("Không được bỏ trống tên tác giả vui lòng nhập lại : ");
            } else {
                return author;
            }
        } while (true);
    }

    public String inputPublisher(Scanner scanner) {
        do {
            System.out.println("Nhập vào nhà xuất bản : ");
            String publisher = scanner.nextLine();
            if (publisher.trim().isEmpty()) {
                System.out.println("không được bỏ trống tên tác giả vui lòng nhập lại : ");
            } else {
                return publisher;
            }
        } while (true);
    }

    public int inputYear(Scanner scanner) {
        do {
            System.out.println("Nhập vào năm xuất bản : ");
            int year = Integer.parseInt(scanner.nextLine());
            if (year >= 1970 && year <= LocalDate.now().getYear()) {
                return year;
            } else {
                System.out.println("Năm xuất bản từ nhận từ năm 1970 đến hiện tại");
            }

        } while (true);
    }

    public String inputDescription(Scanner scanner) {
        do {
            System.out.println("Nhập vào mô tả sách : ");
            String description = scanner.nextLine();
            if (description.trim().isEmpty()) {
                System.out.println("Không được bỏ trống mô thả sách vui lòng nhâp lại : ");
            } else {
                return description;
            }
        } while (true);
    }

    public int inputCategoryId(Scanner scanner) {
        listCategory = Category.readDataToFile();
        do {
            System.out.println("Mã thể loại đã lưu");
            for (int i = 0; i < listCategory.size(); i++) {
                System.out.println(" " + listCategory.get(i).getId() + " : " + listCategory.get(i).getName());
            }


            System.out.println("Nhập vào mã thể loại:");
            int categoryId = 0;
            try {
                categoryId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("vui lòng nhập số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
            for (Category category : listCategory) {
                if (category.getId() == categoryId && category.getStatus()) {
                    return categoryId;
                }
            }
            System.out.println("không tồn tại mã thể loại trên");
        } while (true);
    }

    public String toString() {
        return
                "    '" + id + '\'' +
                        ",        '" + title + '\'' +
                        ",        '" + author + '\'' +
                        ",         " + year +
                        ",        '" + publisher + '\'' +
                        ",         " + description + '\'' +
                        ",         " + categoryId;

    }


    public void updateData(Scanner scanner) {
        listbooks = Book.readDataToFile();
        boolean isExitUpdate = true;
        do {
            System.out.println("Câp nhât thông tin");
            System.out.println("1. Cập nhât title<tiêu đề sách>");
            System.out.println("2. Cập nhât author<tác giả>");
            System.out.println("3. Cập nhât publisher<nhà xuất bản>");
            System.out.println("4. Cập nhật year<năm>");
            System.out.println("5. Cập nhật description<mô tả sách>");
            System.out.println("6. Cập nhật categoryId<mã thể looại>");
            System.out.println("7. Thoát");
            System.out.println("Lựa chon của bạn:");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        this.title = inputTitle(scanner);
                        Book.writeDataToFile(listbooks);
                        break;
                    case 2:
                        this.author = inputAuthor(scanner);
                        Book.writeDataToFile(listbooks);
                        break;
                    case 3:
                        this.publisher = inputPublisher(scanner);
                        Book.writeDataToFile(listbooks);
                        break;
                    case 4:
                        this.year = inputYear(scanner);
                        Book.writeDataToFile(listbooks);
                        break;
                    case 5:
                        this.description = inputDescription(scanner);
                        Book.writeDataToFile(listbooks);
                        break;
                    case 6:
                        this.categoryId = inputCategoryId(scanner);
                        Book.writeDataToFile(listbooks);
                        break;
                    case 7:
                        isExitUpdate = false;
                        break;
                    default:
                        System.out.println("nhập lựa chọn từ 1-8");
                }
            } catch (NumberFormatException e) {
                System.err.println("vui lòng nhâp số nguyên!");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

        } while (isExitUpdate);
    }

    public static void header() {
        String header = "|      ID         |      Title      |      Author     | Year  |    Publisher    |   Description   |   Category ID   |";
        System.out.printf("\n"
                + header + "\n"

        );
    }

    public void deleteBook(Scanner scanner) {
        listbooks = Book.readDataToFile();
        System.out.println("Nhập vào  mã sách cần xóa : ");
        try {
            String nameBook = scanner.nextLine();
            for (int i = 0; i < listbooks.size(); i++) {
                if (listbooks.get(i).getId().equals(nameBook)) {
                    listbooks.remove(i);
                    Book.writeDataToFile(listbooks);
                    System.err.println("Đã xóa thành công");
                    return;
                }
            }
            System.err.println("Không có mã sách này ");
        } catch (Exception e) {
            System.err.println("có lỗi khi nhập vào mã sách");
        }
    }

    public static void writeDataToFile(List<Book> bookList) {
        // ghi file
        File file = new File("book.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(bookList);
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

    public static List<Book> readDataToFile() {
        List<Book> bookListRead = null;
        File file = new File("book.txt");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            bookListRead = (List<Book>) ois.readObject();
            return bookListRead;
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




