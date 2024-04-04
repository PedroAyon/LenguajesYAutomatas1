// transform this class into a normal class

public class SymTableRow {
    private String name;
    private String dataType;
    private String scope;
    private Visibility visibility;
    private Integer size;
    private Integer row;
    private Integer column;
    private Role role;


    public SymTableRow() {
    }

    public SymTableRow(String name, String dataType, String scope, Visibility visibility, Integer size, Integer row, Integer column, Role role) {
        this.name = name;
        this.dataType = dataType;
        this.scope = scope;
        this.visibility = visibility;
        this.size = size;
        this.row = row;
        this.column = column;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }

    public String getScope() {
        return scope;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public Role getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String toString() {
        return "Name: " + name + ", DataType: " + dataType + ", Scope: " + scope + ", Visibility: " + visibility + ", Size: " + size + ", Row: " + row + ", Column: " + column + ", Role: " + role;
    }
}