package Code;

import java.util.ArrayList;
public class User {
    private String id;
    private String password;
    private Role role;

    public User(String id, String password, Role role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }

    public boolean login(String id, String pw) {
        return this.id.equals(id) && this.password.equals(pw);
    }

    public String getId() { return id; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public void akses() { role.getRoleInfo(); }

    public String serialize() {
        String roleStr = (role instanceof Admin) ? "admin" : "produsen";
        return id + ";" + password + ";" + roleStr;
    }

    public static User deserialize(String line, ArrayList<Produk> produkGlobal,
        ArrayList<Produk> produkKonfirmasi,
        ArrayList<Produk> distribusi, ArrayList<String> laporan) {
        String[] parts = line.split(";");
        if(parts.length != 3) return null;
        String id = parts[0].trim();
        String password = parts[1].trim();
        String roleStr = parts[2].trim();
        Role role = null;
        if(roleStr.equalsIgnoreCase("admin")) {
            role = new Admin(produkKonfirmasi, distribusi, laporan);
        } else if(roleStr.equalsIgnoreCase("produsen")) {
            role = new Produsen(id);
        }
        if(role == null) return null;
        return new User(id, password, role);
    }
}