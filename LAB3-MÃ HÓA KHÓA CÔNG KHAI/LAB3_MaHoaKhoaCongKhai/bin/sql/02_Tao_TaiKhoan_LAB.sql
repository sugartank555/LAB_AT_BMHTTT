-- Chay tren SQL Server ban dung cho LAB. Thay mat khau placeholder truoc khi Execute.
USE master;
GO
IF SUSER_ID(N'lab3_user') IS NULL
 CREATE LOGIN lab3_user WITH PASSWORD = 'CHANGE_ME_StrongPassword_2026!', CHECK_POLICY = ON;
GO
USE LAB3_MaHoaKhoaCongKhai;
GO
IF DATABASE_PRINCIPAL_ID(N'lab3_user') IS NULL CREATE USER lab3_user FOR LOGIN lab3_user;
GO
GRANT SELECT, INSERT ON OBJECT::dbo.History TO lab3_user;
GO
