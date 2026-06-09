using Microsoft.EntityFrameworkCore;
using NotePlusTaller.API.Models;

namespace NotePlusTaller.API.Data;

public class AppDbContext(DbContextOptions<AppDbContext> options) : DbContext(options)
{
    public DbSet<Nacionalidad> Nacionalidades => Set<Nacionalidad>();
    public DbSet<Persona> Personas => Set<Persona>();
    public DbSet<Rol> Roles => Set<Rol>();
    public DbSet<Usuario> Usuarios => Set<Usuario>();

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<Nacionalidad>(e =>
        {
            e.ToTable("Nacionalidad");
            e.HasKey(n => n.IDNacionalidad);
            e.Property(n => n.NombreNacionalidad).IsRequired().HasMaxLength(100);
        });

        modelBuilder.Entity<Persona>(e =>
        {
            e.ToTable("Persona");
            e.HasKey(p => p.IDPersona);
            e.HasOne(p => p.Nacionalidad)
             .WithMany(n => n.Personas)
             .HasForeignKey(p => p.IDNacionalidad);
        });

        modelBuilder.Entity<Rol>(e =>
        {
            e.ToTable("Rol");
            e.HasKey(r => r.IDRol);
            e.Property(r => r.NombreRol).IsRequired().HasMaxLength(100);
        });

        modelBuilder.Entity<Usuario>(e =>
        {
            e.ToTable("Usuario");
            e.HasKey(u => u.IDUsuario);
            e.HasOne(u => u.Rol).WithMany(r => r.Usuarios).HasForeignKey(u => u.IDRol);
            e.HasOne(u => u.Persona).WithOne(p => p.Usuario).HasForeignKey<Usuario>(u => u.IDPersona);
        });
    }
}
