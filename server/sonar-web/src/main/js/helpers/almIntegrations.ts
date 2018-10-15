/*
 * SonarQube
 * Copyright (C) 2009-2018 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
import { isLoggedIn } from './users';
import { CurrentUser } from '../app/types';

export function hasAdvancedALMIntegration(user: CurrentUser) {
  return (
    isLoggedIn(user) && (isBitbucket(user.externalProvider) || isGithub(user.externalProvider))
  );
}

export function isBitbucket(almId?: string) {
  return almId && almId.startsWith('bitbucket');
}

export function isGithub(almId?: string) {
  return almId === 'github';
}

export function isVSTS(almId?: string) {
  return almId === 'microsoft';
}

export function sanitizeAlmId(almId?: string) {
  if (isBitbucket(almId)) {
    return 'bitbucket';
  }
  return almId;
}