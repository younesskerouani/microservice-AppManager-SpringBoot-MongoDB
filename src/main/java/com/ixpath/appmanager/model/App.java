package com.ixpath.appmanager.model;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor 
@AllArgsConstructor  
@ToString
public class App {
    private @Getter @Setter Map<String, Object> global;
    private @Getter @Setter List<Object> navigation;
    private @Getter @Setter Boolean system = false;
    private @Getter @Setter Boolean active = false;
    private @Getter @Setter Boolean draft = true;
}
