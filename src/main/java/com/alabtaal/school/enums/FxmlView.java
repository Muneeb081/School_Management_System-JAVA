package com.alabtaal.school.enums;

import java.util.ResourceBundle;

public enum FxmlView {

    ADMIN{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("admin.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/admin.fxml";
        }
    },
    LOGIN{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/login.fxml";
        }
    },

    MENU{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("menu.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/menu.fxml";
        }
    },

    STUDENTS_MODULE{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("studentRecord.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/studentRecord.fxml";
        }
    },

    FEE_SUBMISSION_MODULE{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("feeCounter.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/feeCounter.fxml";
        }
    },

    TEACHERS_MODULE{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("teacherRecord.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/teacherRecord.fxml";
        }
    },

    STUDENTS_REPORT_CARD{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("result.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/result.fxml";
        }
    },
    Master_Table{
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("master.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/master.fxml";
        }
    }
    ;

    static String getStringFromResourceBundle(String key)
    {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
    public abstract  String getTitle();
    public abstract  String getFxmlFile();

}