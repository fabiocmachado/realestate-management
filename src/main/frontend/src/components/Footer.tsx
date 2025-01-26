import React from "react";
import '../styles/footer.css'

 interface FooterProps {
   className?: string;
 }

 const Footer: React.FC<FooterProps> = ({ className }) => {
   return (
     <footer className={`footer ${className || ''}`}>
       <p>© 2024 Meu Sistema de Imóveis. Todos os direitos reservados.</p>
     </footer>
   );
 };

 export default Footer;