// PhoneInputField.tsx
import React from 'react';

interface PhoneInputFieldProps {
  phone: string;
  onPhoneChange: (phone: string) => void;
  error?: string;
}

const PhoneInputField: React.FC<PhoneInputFieldProps> = ({ phone, onPhoneChange, error }) => {
  return (
    <div>
      <input
        type="tel"
        value={phone}
        onChange={(e) => onPhoneChange(e.target.value)}
        placeholder="Digite o número de telefone"
      />
      {error && <p className="error-message">{error}</p>}
    </div>
  );
};

export default PhoneInputField;
