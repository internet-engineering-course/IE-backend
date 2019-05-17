package ir.ac.ut.joboonja.database;

import java.util.List;

public class PreparedQuery {
    private String preparedSql;
    private List<Object> parameters;

    public PreparedQuery(String preparedSql, List<Object> parameters) {
        this.preparedSql = preparedSql;
        this.parameters = parameters;
    }

    public String getPreparedSql() {
        return preparedSql;
    }

    public void setPreparedSql(String preparedSql) {
        this.preparedSql = preparedSql;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public void setParameters(List<Object> parameters) {
        this.parameters = parameters;
    }
}
