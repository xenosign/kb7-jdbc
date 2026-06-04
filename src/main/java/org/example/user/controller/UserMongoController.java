package org.example.user.controller;

import org.example.user.dto.UserCreateRequest;
import org.example.user.dto.UserResponse;
import org.example.user.entity.User;
import org.example.user.service.UserMongoService;

import java.util.List;
import java.util.Scanner;

public class UserMongoController {
    public static void main(String[] args) {
        UserMongoService userService = new UserMongoService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== 회원 관리 프로그램 (MongoDB) ======");
            System.out.println("1. 회원 목록 조회");
            System.out.println("2. 회원 추가");
            System.out.println("3. 특정 이름이 포함된 유저 검색");
            System.out.println("4. 회원 삭제");
            System.out.println("5. 회원 정보 수정");
            System.out.println("6. 종료");
            System.out.print("원하는 작업 번호를 입력하세요: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                // 1. 회원 목록 조회
                List<UserResponse> users = userService.getAllUsers();
                for (UserResponse user : users) {
                    System.out.println(user.toString());
                }

            } else if (choice == 2) {
                // 2. 회원 추가
                System.out.print("USER ID: ");
                String userId = scanner.nextLine();
                System.out.print("이름: ");
                String name = scanner.nextLine();
                System.out.print("비밀번호: ");
                String password = scanner.nextLine();

                UserCreateRequest newUser = new UserCreateRequest();
                newUser.setUserId(userId);
                newUser.setName(name);
                newUser.setPassword(password);

                int result = userService.addUser(newUser);
                System.out.println("추가된 회원 수 : " + result);

            } else if (choice == 3) {
                // 3. 이름 검색
                System.out.print("검색할 이름: ");
                String name = scanner.nextLine();

                List<User> result = userService.searchByName(name);
                for (User user : result) {
                    System.out.println(user.toString());
                }

            } else if (choice == 4) {
                // 4. 회원 삭제
                System.out.print("삭제할 회원의 PK: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                int deleted = userService.deleteUser(id);
                if (deleted > 0) {
                    System.out.println("회원 삭제 완료. 삭제된 행 수: " + deleted);
                } else {
                    System.out.println("해당 ID 를 가지는 회원이 없습니다");
                }

            } else if (choice == 5) {
                // 5. 회원 정보 수정
                System.out.print("수정할 회원의 PK: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("새 ID: ");
                String newUserId = scanner.nextLine();
                System.out.print("새 이름: ");
                String newName = scanner.nextLine();
                System.out.print("새 비밀번호: ");
                String newPassword = scanner.nextLine();

                User updateUser = new User();
                updateUser.setId(id);
                updateUser.setUserId(newUserId);
                updateUser.setName(newName);
                updateUser.setPassword(newPassword);

                int updated = userService.updateUser(updateUser);
                System.out.println("수정된 행 수: " + updated);

            } else if (choice == 6) {
                // 6. 종료
                System.out.println("프로그램을 종료합니다.");
                break;

            } else {
                System.out.println("잘못된 번호입니다. 다시 입력하세요.");
            }
        }

        scanner.close();
    }
}
