package group.securitystudy.webapi.mapper;


import group.securitystudy.feature.model.Users;
import group.securitystudy.webapi.form.UserForm;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        implementationName = "UserFormMapperImpl",
        builder = @Builder(disableBuilder = true)
)
public abstract class UserFormMapper {

    public abstract Users toUser(UserForm.Input.Add in);

    public abstract UserForm.Output.GetAll toGet(Users in);

    public abstract List<UserForm.Output.GetAll> toGetAllList(List<Users> in);

}
