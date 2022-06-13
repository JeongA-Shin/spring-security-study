package group.securitystudy.webapi.form;

import lombok.*;

public class UserForm {

    public static class Input{

        @Data
        @Builder
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        //검색시 전달 받을 값
        public static class GetAll{
            private String email;
        }

        @Data
        @Builder
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Add{
            private String email;
            private String password;
            private String auth;
        }

        @Data
        @Builder
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Login {

            private String email;
            private String password;
        }


    }


    public static class Output{

        @Data
        @Builder
        @ToString
        @NoArgsConstructor
        @AllArgsConstructor
        public static class GetAll{
            private String email;
            private String password;
            private String auth;
        }

    }
}
