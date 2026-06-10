class Rol
{
    public Rol() { }
    int Id { get; set; }
    string NombreRol { get; set; }  
    public Rol(int id, string nombreRol)
    {
        this.Id = id;
        this.NombreRol = nombreRol;
    }
}