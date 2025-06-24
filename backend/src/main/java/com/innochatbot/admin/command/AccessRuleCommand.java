package com.innochatbot.admin.command;

import lombok.Data;

@Data
public class AccessRuleCommand {

    String pathId;
    String path;
    String accessId;
}
