import React from "react";

interface FooterProps {
  className?: string;
}

const Footer: React.FC<FooterProps> = ({ className }) => {
  return (
    <footer className={`bg-gray-100 text-center py-4 text-gray-600 text-sm border-t border-gray-200 ${className || ''}`}>
      <p>© 2024 Meu Sistema de Imóveis. Todos os direitos reservados.</p>
    </footer>
  );
};

export default Footer;
