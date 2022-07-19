package springasync.practice.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

// Github 유저 정보를 표현하여 GitHub’s API를 통해 받아올 data의 표현을 정의합니다.
// define a representation for the data you will retrieve through GitHub’s API.

// @JsonIgnoreProperties annotation은 클래스에 없는 속성의 경우(Unknown)
// The @JsonIgnoreProperties annotation tells Spring to ignore any attributes not listed in the class.

// 시범용으로 간단히 보여주기 위해 name과 blog 속성만을 전달받는다.
// In this guide, we grab only the name and the blog URL for demonstration purposes.
@JsonIgnoreProperties(ignoreUnknown=true)
// Lombok의 기능으로 추가한 코드입니다.(번외)
@Getter @Setter
public class User {
    private String name;
    private String blog;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", blog='" + blog + '\'' +
                '}';
    }
}
