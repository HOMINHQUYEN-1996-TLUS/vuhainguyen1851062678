package bomoncntt.svk60.vuhainguyen1851062678.model;

public class student {
    private String stdid;
    private String fullname;
    private String sex;
    private String grade;
    private String stdimage;

    public student(String stdid, String fullname, String sex, String grade, String stdimage) {
        this.stdid = stdid;
        this.fullname = fullname;
        this.sex = sex;
        this.grade = grade;
        this.stdimage = stdimage;
    }
    public student(){}

    public String getStdid() {
        return stdid;
    }

    public void setStdid(String stdid) {
        this.stdid = stdid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStdimage() {
        return stdimage;
    }

    public void setStdimage(String stdimage) {
        this.stdimage = stdimage;
    }

    @Override
    public String toString() {
        return "student{" +
                "stdid='" + stdid + '\'' +
                ", fullname='" + fullname + '\'' +
                ", sex='" + sex + '\'' +
                ", grade='" + grade + '\'' +
                ", stdimage='" + stdimage + '\'' +
                '}';
    }
}
