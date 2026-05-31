package org.example.user.controller;

import org.example.user.entity.User;
import org.example.user.service.UserService;

import java.util.List;
import java.util.Scanner;

public class UserController {
    // 1. REPO 를 stmt 에서 pstmt 로 수정해서 레이어 분리 보여주기
    // 2. 이름 검색을 어플리케이션 레벨에서 -> Repo 로 수정 보여주기

    public static void main(String[] args) {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== 회원 관리 프로그램 ======");
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
                List<User> users = userService.getAllUsers();
                for (User user : users) {
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

                User newUser = new User();
                newUser.setUserId(userId);
                newUser.setName(name);
                newUser.setPassword(password);

                int generatedId = userService.addUser(newUser);
                System.out.println("추가된 회원 PK: " + generatedId);

            } else if (choice == 3) {
                // 3. 이름 검색
                System.out.print("검색할 이름: ");
                String name = scanner.nextLine();

                List<User> result = userService.searchByNameLike(name);
                for (User user : result) {
                    System.out.println(user.toString());
                }

            } else if (choice == 4) {
                // 4. 회원 삭제
                System.out.print("삭제할 회원의 PK: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                int deleted = userService.deleteUser(id);
                System.out.println("삭제된 행 수: " + deleted);

            } else if (choice == 5) {
                // 5. 회원 정보 수정
                System.out.print("수정할 회원의 PK: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("새 이름: ");
                String newName = scanner.nextLine();
                System.out.print("새 비밀번호: ");
                String newPassword = scanner.nextLine();

                User updateUser = new User();
                updateUser.setId(id);
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
